import numpy as np
import cv2 as cv

# Create a black image
img = np.zeros((512, 512, 3), np.uint8)
rotation = 0
cv.ellipse(img, (256, 256-100), (100, 100), rotation, 0, 180, (255, 0, 0), 3)
# rotate 180 degrees
cv.ellipse(img, (256, 256+100), (100, 100), rotation, 180, 360, (255, 0, 0), 3)
# rotate 90 degrees
cv.ellipse(img, (256-100, 256), (100, 100), rotation, 90, 270, (255, 255, 0), 3)
# rotate 270 degrees
cv.ellipse(img, (256+100, 256), (100, 100), rotation, 270, 360+90, (255, 255, 0), 3)

# make a rainbow circle with 7 colors
for i in range(7):
    cv.circle(img, (256, 256), 63 - i * 10, (0, 255 - i * 30, 255), 3)

cv.imshow('image', img)
cv.waitKey(0)
