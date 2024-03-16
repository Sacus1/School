import cv2
import mediapipe as mp
import time

import numpy as np


# mpDraw = mp.solutions.drawing_utils
# mpPose = mp.solutions.pose
# pose = mpPose.Pose()
# cap = cv2.VideoCapture(0)
# pTime = 0
#
# while True:
#     success, img = cap.read()
#     imgRGB = cv2.cvtColor(img, cv2.COLOR_BGR2RGB)
#     results = pose.process(imgRGB)
#     if results.pose_landmarks:
#         mpDraw.draw_landmarks(img, results.pose_landmarks, mpPose.POSE_CONNECTIONS)
#         for id, lm in enumerate(results.pose_landmarks.landmark):
#             h, w, c = img.shape
#             cx, cy = int(lm.x * w), int(lm.y * h)
#             cv2.circle(img, (cx, cy), 5, (255, 0, 0), cv2.FILLED)
#     cTime = time.time()
#     fps = 1 / (cTime - pTime)
#     pTime = cTime
#     cv2.putText(img, f'FPS: {int(fps)}', (20, 70), cv2.FONT_HERSHEY_PLAIN, 3, (255, 0, 0), 3)
#     cv2.imshow("Image", img)
#     if cv2.waitKey(1) & 0xFF == ord('q'):
#         break

class poseDetector:
    def __init__(self, video=0, topBody=False,loop=False):
        self.topBody = topBody
        self.mpDraw = mp.solutions.drawing_utils
        self.mpPose = mp.solutions.pose
        self.pose = self.mpPose.Pose()
        self.cap = cv2.VideoCapture(video)
        self.pTime = 0
        self.image = self.cap.read()[1]
        self.positions = []

    def findPose(self, draw=True):
        ret, self.image = self.cap.read()
        if not ret:  # If video has ended
            self.cap.set(cv2.CAP_PROP_POS_FRAMES, 0)  # Reset video to start
            ret, self.image = self.cap.read()  # Read the first frame of the video
        # rescale image to 600x800
        self.image = cv2.resize(self.image, (1200, 800))
        imgRGB = cv2.cvtColor(self.image, cv2.COLOR_BGR2RGB)
        results = self.pose.process(imgRGB)
        if results.pose_landmarks:
            if draw:
                self.positions.clear()
                self.mpDraw.draw_landmarks(self.image, results.pose_landmarks, self.mpPose.POSE_CONNECTIONS)
                for id, lm in enumerate(results.pose_landmarks.landmark):
                    if self.topBody and id in [24, 23, 26, 25, 28, 27, 30, 29, 31, 32]:
                        continue
                    h, w, c = self.image.shape
                    cx, cy = int(lm.x * w), int(lm.y * h)
                    cv2.circle(self.image, (cx, cy), 5, (255, 0, 0), cv2.FILLED)
                    self.positions.append([id, cx, cy])

    def drawImage(self):
        cTime = time.time()
        fps = 1 / (cTime - self.pTime)
        self.pTime = cTime
        cv2.putText(self.image, f'FPS: {int(fps)}', (20, 70), cv2.FONT_HERSHEY_PLAIN, 3, (255, 0, 0), 3)
        cv2.imshow("Image", self.image)

    def get_angle(self, point1, point2, point3):
        cv2.circle(self.image, (self.positions[point1][1], self.positions[point1][2]), 20, (0, 0, 255))
        cv2.circle(self.image, (self.positions[point2][1], self.positions[point2][2]), 20, (0, 0, 255))
        cv2.circle(self.image, (self.positions[point3][1], self.positions[point3][2]), 20, (0, 0, 255))
        cv2.line(self.image, (self.positions[point1][1], self.positions[point1][2]),
                 (self.positions[point2][1], self.positions[point2][2]), (0, 255, 255), 3)
        cv2.line(self.image, (self.positions[point2][1], self.positions[point2][2]),
                 (self.positions[point3][1], self.positions[point3][2]), (0, 255, 255), 3)
        x1, y1 = self.positions[point1][1], self.positions[point1][2]
        x2, y2 = self.positions[point2][1], self.positions[point2][2]
        x3, y3 = self.positions[point3][1], self.positions[point3][2]
        angle = abs(int(np.degrees(np.arctan2(y3 - y2, x3 - x2) - np.arctan2(y1 - y2, x1 - x2))))
        if angle > 180:
            angle = 360 - angle
        return angle


if __name__ == '__main__':
    self = poseDetector(0)
    while True:
        self.findPose()
        self.drawImage()
        if cv2.waitKey(1) & 0xFF == ord('q'):
            break
