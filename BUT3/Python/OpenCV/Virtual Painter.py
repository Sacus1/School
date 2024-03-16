import numpy as np

import hand as hand
import cv2
import colorsys

detector = hand.handDetector(detectionConfidence=0.8, maxHands=1)
points = []


class Point:
    def __init__(self, x, y, color, precedent):
        self.x = x
        self.y = y
        self.color = color
        self.precedent = precedent

    def __str__(self):
        return f'({self.x}, {self.y})'

    def __repr__(self):
        return f'({self.x}, {self.y})'

    def __add__(self, other):
        return Point(self.x + other.x, self.y + other.y)

    def __eq__(self, other):
        if not other:
            return False
        return self.x == other.x and self.y == other.y

    def __hash__(self):
        return hash((self.x, self.y))

    def draw(self, img):
        if self.precedent:
            cv2.line(img, (self.x, self.y), (self.precedent.x, self.precedent.y), self.color, 15)


mode = 0  # 0 : nothing, 1 : draw, 2 : select color
color = (0, 0, 255)
eraser = False;
draw_areas = False;


def get_color( x, width):
    hue = x / width
    r, g, b = colorsys.hsv_to_rgb(hue, 1, 1)
    return b * 255, g * 255, r * 255


def virtualPainter(self):
    hue_band = np.zeros((100, 3 * self.img.shape[1] // 4, 3), dtype=np.uint8)
    for i in range(hue_band.shape[1]):
        hue = i / hue_band.shape[1]
        r, g, b = colorsys.hsv_to_rgb(hue, 1, 1)
        hue_band[:, i] = (b * 255, g * 255, r * 255)
    # draw hue band
    self.img[0:100, 0:hue_band.shape[1]] = hue_band
    # zone is 50 pixels from each side of the image
    zone = [50, 100, self.img.shape[1] - 50, self.img.shape[0] - 50]
    # red_selector = (0, 0, self.img.shape[1] // 4, 100)
    # green_selector = (self.img.shape[1] // 4, 0, self.img.shape[1] // 2, 100)
    # blue_selector = (self.img.shape[1] // 2, 0, 3 * self.img.shape[1] // 4, 100)
    erase_selector = (3 * self.img.shape[1] // 4, 0, self.img.shape[1], 100)
    global mode
    global color
    global eraser
    if draw_areas:
    #     # draw zone
    #     cv2.rectangle(self.img, (zone[0], zone[1]), (zone[2], zone[3]), (0, 255, 0), 2)
    #     cv2.rectangle(self.img, (red_selector[0], red_selector[1]), (red_selector[2], red_selector[3]), (0, 0, 255), 2)
    #     cv2.rectangle(self.img, (green_selector[0], green_selector[1]), (green_selector[2], green_selector[3]),
    #                   (0, 255, 0),
    #                   2)
    #     cv2.rectangle(self.img, (blue_selector[0], blue_selector[1]), (blue_selector[2], blue_selector[3]), (255, 0, 0),
    #                   2)
        cv2.rectangle(self.img, (erase_selector[0], erase_selector[1]), (erase_selector[2], erase_selector[3]),
                      (0, 0, 0),
                      2)
    # draw selection eraser
    cv2.line(self.img, (520, 63), (572, 20), (0, 0, 0), 2)
    cv2.line(self.img, (520, 63), (546, 84), (0, 0, 0), 2)
    cv2.line(self.img, (546, 84), (597, 41), (0, 0, 0), 2)
    cv2.line(self.img, (571, 19), (597, 41), (0, 0, 0), 2)
    cv2.line(self.img, (547, 40), (572, 62), (0, 0, 0), 2)


    # fill the circle if the color is selected
    # if color == (0, 0, 255):
    #     cv2.circle(self.img, get_center(red_selector), 20, (0, 0, 255), cv2.FILLED)
    # if color == (0, 255, 0):
    #     cv2.circle(self.img, get_center(green_selector), 20, (0, 255, 0), cv2.FILLED)
    # elif color == (255, 0, 0):
    #     cv2.circle(self.img, get_center(blue_selector), 20, (255, 0, 0), cv2.FILLED)
    if eraser:
        # fill the upper part of the eraser
        cv2.fillPoly(self.img, [np.array([(546, 41), (572, 20), (597, 41), (571, 62)])], (0, 0, 0))
    else :
        #get position of the selected color
        hsv = cv2.cvtColor(np.uint8([[color]]), cv2.COLOR_BGR2HSV)
        pos = (int(hsv[0][0][0] * hue_band.shape[1] / 180), 50)
        # draw a line to the selected color
        cv2.line(self.img, (pos[0],0), (pos[0], 99), (0, 0, 0), 2)

    fingers = self.get_up_fingers()
    cv2.circle(self.img, (self.fingersPos[8][0], self.fingersPos[8][1]), 20, color, cv2.FILLED)

    # check if only index finger is up
    if fingers[0] and not fingers[1] and not fingers[2] and not fingers[3]:
        if zone[0] < self.fingersPos[8][0] < zone[2] and zone[1] < self.fingersPos[8][1] < zone[3]:
            if eraser:
                # remove point near the finger
                for p in points:
                    if not p:
                        continue
                    if p and abs(p.x - self.fingersPos[8][0]) < 20 and abs(p.y - self.fingersPos[8][1]) < 20:
                        points.remove(p)
            else:
                points.append(
                    Point(self.fingersPos[8][0], self.fingersPos[8][1], color, points[-1] if len(points) > 0 else None))
        elif mode == 1:
            points.append(None)
        mode = 1

    # if index and middle fingers are up
    elif fingers[0] and fingers[1] and not fingers[2] and not fingers[3]:
        if mode == 1:
            points.append(None)
        # check if the user is selecting a color
        # if red_selector[0] < self.fingersPos[12][0] < red_selector[2] and red_selector[1] < self.fingersPos[12][1] < \
        #         red_selector[3]:
        #     color = (0, 0, 255)
        #     eraser = False
        # elif green_selector[0] < self.fingersPos[12][0] < green_selector[2] and green_selector[1] < \
        #         self.fingersPos[12][1] < green_selector[3]:
        #     color = (0, 255, 0)
        #     eraser = False
        # elif blue_selector[0] < self.fingersPos[12][0] < blue_selector[2] and blue_selector[1] < \
        #         self.fingersPos[12][1] < blue_selector[3]:
        #     color = (255, 0, 0)
        #     eraser = False
        if erase_selector[0] < self.fingersPos[12][0] < erase_selector[2] and erase_selector[1] < \
                self.fingersPos[12][1] < erase_selector[3]:
            eraser = True
            color = (0, 0, 0)
        elif hue_band.shape[1] > self.fingersPos[8][0] > 0 and 0 < self.fingersPos[8][1] < 100:
            # Get the color at the position of the user's finger on the hue band
            color = get_color(self.fingersPos[8][0], hue_band.shape[1])

            # Set this color as the current drawing color
            self.color = color
            eraser = False
        mode = 2
    else:
        if mode == 1:
            points.append(None)
        mode = 0

    for p in points:
        if p:
            p.draw(self.img)


def get_center(zone):
    return (zone[0] + zone[2]) // 2, (zone[1] + zone[3]) // 2


detector.function = virtualPainter
detector.run()
