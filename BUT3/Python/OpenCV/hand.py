from threading import Thread

import cv2 as cv
import mediapipe as mp
import time
import numpy as np


class handDetector:
    def __init__(self, detectionConfidence=0.5, maxHands=1):
        self.maxHands = maxHands
        self.mpHands = mp.solutions.hands
        self.hands = self.mpHands.Hands(max_num_hands=self.maxHands, min_detection_confidence=detectionConfidence)
        self.mpDraw = mp.solutions.drawing_utils
        self.cap = cv.VideoCapture(0)
        self.pTime = 0
        self.fingersPos = np.array([[0, 0]] * 21)
        self.distancePercent = 0
        self.distanceMin = 50
        self.distanceMax = 300
        self.img = self.cap.read()[1]
        # method to call when the image is updated
        self.function = None

    def drawDistance(self) -> None:
        # draw blue rectangle
        cv.rectangle(self.img, (0, 0), (int(self.distancePercent * 6.4), 50), (255, 0, 0), cv.FILLED)
        cv.putText(self.img, f'{int(self.distancePercent)}%', (10, 30), cv.FONT_HERSHEY_PLAIN, 2, (255, 0, 255), 2)
        cv.line(self.img, self.fingersPos[8], self.fingersPos[4], (255, 0, 0), 2)
        cv.circle(self.img, self.fingersPos[8], 10, (255, 0, 0), cv.FILLED)
        cv.circle(self.img, self.fingersPos[4], 10, (255, 0, 0), cv.FILLED)
        cv.circle(self.img, (self.fingersPos[4]+self.fingersPos[8])//2, 10, (255, 0, 0), cv.FILLED)
    def run(self):
        while True:
            fps = 1 / (time.time() - self.pTime)
            self.pTime = time.time()
            success, self.img = self.cap.read()
            if not success:
                print("Failed to read frame from camera. Please check your camera.")
                return
            imgRGB = cv.cvtColor(self.img, cv.COLOR_BGR2RGB)

            results = self.hands.process(imgRGB)

            self.FindHand(results)

            cv.putText(self.img, str(int(fps)), (10, 70), cv.FONT_HERSHEY_PLAIN, 3, (255, 0, 255), 3)
            self.function(self)

            cv.imshow('image', self.img)
            if cv.waitKey(1) & 0xFF == ord('q'):
                break

    def FindHand(self, results):
        if results.multi_hand_landmarks:
            for handLms in results.multi_hand_landmarks:
                self.mpDraw.draw_landmarks(self.img, handLms, self.mpHands.HAND_CONNECTIONS)
                landmarks = handLms.landmark
                h, w, c = self.img.shape
                for id, lm in enumerate(landmarks):
                    cx, cy = int(lm.x * w), int(lm.y * h)
                    self.fingersPos[id] = [cx, cy]
            distance = int(cv.norm(self.fingersPos[4], self.fingersPos[8]))

            self.distancePercent = np.interp(min(max(distance, self.distanceMin), self.distanceMax),
                                             [self.distanceMin, self.distanceMax], [0, 100])

    def get_amount_fingers(self):
        """
        Returns the amount of fingers raised
        [index, middle, ring, pinky, thumb]
        """
        fingers = []
        # index
        fingers.append(self.fingersPos[8][1] < self.fingersPos[7][1])
        # middle
        fingers.append(self.fingersPos[12][1] < self.fingersPos[11][1])
        # ring
        fingers.append(self.fingersPos[16][1] < self.fingersPos[15][1])
        # pinky
        fingers.append(self.fingersPos[20][1] < self.fingersPos[19][1])
        # thumb
        # check if thumb is on the right or left by checking position relative to the middle finger
        if self.fingersPos[4][0] > self.fingersPos[12][0]:
            fingers.append(self.fingersPos[4][0] > self.fingersPos[3][0])
        else:
            fingers.append(self.fingersPos[4][0] < self.fingersPos[3][0])
        return fingers

    def __del__(self):
        self.cap.release()
        cv.destroyAllWindows()
