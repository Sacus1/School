import cv2 as cv
import numpy as np

pointsDepart = [(111, 219), (287, 188), (352, 440), (154, 482)]
pointsArrivee = [(0, 0), (352, 0), (352, 500), (0, 500)]


def onMouseClick(event, x, y, flags, param):
    if event == cv.EVENT_LBUTTONDOWN:
        if len(pointsDepart) >= 4:
            pointsDepart.clear()
        pointsDepart.append((x, y))


def scale_down_image(image, scale_percent):
    # calculate the new dimensions
    width = int(image.shape[1] * scale_percent / 100)
    height = int(image.shape[0] * scale_percent / 100)
    new_dimensions = (width, height)

    # resize the image
    resized_image = cv.resize(image, new_dimensions, interpolation=cv.INTER_AREA)

    return resized_image


def detect_document(img):
    # Convert the image to grayscale
    gray = cv.cvtColor(img, cv.COLOR_BGR2GRAY)

    # Blur the image to reduce noise
    blurred = cv.GaussianBlur(gray, (5, 5), 0)

    # Perform edge detection
    edged = cv.Canny(blurred, 50, 150)

    # Find contours in the edge map
    contours, _ = cv.findContours(edged.copy(), cv.RETR_LIST, cv.CHAIN_APPROX_SIMPLE)
    contours = sorted(contours, key = cv.contourArea, reverse = True)[:5]
    biggest = 0
    # Iterate over the contours
    for c in contours:
        # Approximate the contour
        peri = cv.arcLength(c, True)
        approx = cv.approxPolyDP(c, 0.02 * peri, True)
        cv.drawContours(img, [approx], -1, (0, 255, 0), 2)
        # If the approximated contour has four points, we have found the document
        if len(approx) == 4 and cv.contourArea(c) > biggest:
            biggest = cv.contourArea(c)
            pointsDepart.clear()
            min_x = (10000, 0)
            min_y = (0, 10000)
            max_x = (0, 0)
            max_y = (0, 0)
            for point in approx:
                if point[0][0] < min_x[0]:
                    min_x = (point[0][0], point[0][1])
                if point[0][1] < min_y[1]:
                    min_y = (point[0][0], point[0][1])
                if point[0][0] > max_x[0]:
                    max_x = (point[0][0], point[0][1])
                if point[0][1] > max_y[1]:
                    max_y = (point[0][0], point[0][1])
            pointsDepart.append(min_x)
            pointsDepart.append(min_y)
            pointsDepart.append(max_x)
            pointsDepart.append(max_y)
    if biggest == 0:
        return False
    return True


cv.namedWindow('Image')
cv.setMouseCallback('Image', onMouseClick)
# load video from webcam
vid = cv.VideoCapture(0)
# set contrast to 150
vid.set(10, 150)
if __name__ == '__main__':
    pass
while 1:
    img = cv.imread('cards.png')
    # set point arrivé to image size
    pointsArrivee[1] = (0, img.shape[0])
    pointsArrivee[2] = (img.shape[1], img.shape[0])
    pointsArrivee[3] = (img.shape[1], 0)
    # detect point depart
    isDetect = detect_document(img.copy())
    if not isDetect:
        continue
    # add points to image
    for point in pointsDepart:
        cv.circle(img, point, 5, (0, 0, 255), -1)
    if len(pointsDepart) == 4:
        img2 = img.copy()
        if len(pointsArrivee) == 4:
            # draw rectangle
            cv.polylines(img, np.array([pointsArrivee]), True, (0, 0, 255), 2)
            # get perspective
            matrix = cv.getPerspectiveTransform(np.float32(pointsDepart), np.float32(pointsArrivee))
            img2 = cv.warpPerspective(img, matrix, (img.shape[1], img.shape[0]))
    cv.imshow('Image2', img2)
    cv.imshow('Image', img)
    if cv.waitKey(20) & 0xFF == ord('q'):
        break
