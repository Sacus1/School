import time

import cv2

from PoseDetector import poseDetector

path = 'videos/bike.mp4'
detector = poseDetector(path, loop=True)
pushups = 0
tour = 0
positionUp = False


def remap(x, in_min, in_max, out_min, out_max):
    return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min


mini = 1000
maxi = 90
useAngle = False
t1 = time.time()
while True:
    detector.findPose()
    # get angle between an upper arm and lower arm
    if detector.positions and useAngle:
        angle = detector.get_angle(23, 25, 27)
        cv2.rectangle(detector.image, (20, 500), (200, 250), (0, 0, 0))
        if angle < mini:
            mini = angle
        if angle > maxi and angle != mini:
            maxi = angle
        cv2.rectangle(detector.image, (20, 500), (200, int(remap(angle, maxi, mini, 250, 500))), (255, 0, 0),
                      cv2.FILLED)
        if angle >= 120:
            positionUp = True
        if angle <= 90 and positionUp:
            positionUp = False
            pushups += 1
        cv2.putText(detector.image, f'{pushups}', (600, 550), cv2.FONT_HERSHEY_PLAIN, 10,
                    (255, 0, 0), 10)
    else :
        pass
    detector.drawImage()
    if cv2.waitKey(1) & 0xFF == ord('q'):
        break
