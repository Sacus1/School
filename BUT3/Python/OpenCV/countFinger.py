import time
from threading import Thread

import cv2

from hand import handDetector

detector = handDetector()


def drawFingers(self):
    fingers = self.get_up_fingers()
    # get image in Fingers folder
    if fingers[0] and fingers[1] and not fingers[2] and not fingers[3]:
        image = cv2.imread(f'Fingers/v.png')
    elif fingers[0] and not fingers[1] and not fingers[2] and not fingers[3] and fingers[4]:
        image = cv2.imread(f'Fingers/L.png')
    elif fingers[0] and not fingers[1] and not fingers[2] and fingers[3] and fingers[4]:
        image = cv2.imread(f'Fingers/w.png')
    elif not fingers[0] and not fingers[1] and not fingers[2] and fingers[3] and fingers[4]:
        image = cv2.imread(f'Fingers/C.png')
    elif not fingers[0] and fingers[1] and not fingers[2] and not fingers[3]:
        image = cv2.imread(f'Fingers/i.png')
    else:
        amount = sum([1 for i in fingers if i])
        image = cv2.imread(f'Fingers/{amount}.png')
    self.img[0:image.shape[0], 0:image.shape[1]] = image


detector.function = drawFingers
detector.run()

del detector
