import cv2 as cv
# load car.png
img = cv.imread('bestcar.png')
cv.imshow('image', img)
# convert to HSV
imgHSV = cv.cvtColor(img, cv.COLOR_BGR2HSV)
cv.imshow('imageHSV', imgHSV)
# create binary mask using trackbar to define lower and upper bounds
# H: 0-180, S: 0-255, V: 0-255
lower = (0, 0, 0)
upper = (180, 255, 255)
cv.namedWindow('imageBinary')
cv.createTrackbar('H', 'imageBinary', 0, 180, lambda x: globals().update(lower=(x, lower[1], lower[2])))
cv.createTrackbar('S', 'imageBinary', 0, 255, lambda x: globals().update(lower=(lower[0], x, lower[2])))
cv.createTrackbar('V', 'imageBinary', 0, 255, lambda x: globals().update(lower=(lower[0], lower[1], x)))
cv.createTrackbar('H2', 'imageBinary', 180, 180, lambda x: globals().update(upper=(x, upper[1], upper[2])))
cv.createTrackbar('S2', 'imageBinary', 255, 255, lambda x: globals().update(upper=(upper[0], x, upper[2])))
cv.createTrackbar('V2', 'imageBinary', 255, 255, lambda x: globals().update(upper=(upper[0], upper[1], x)))

while 1:
    mask = cv.inRange(imgHSV, lower, upper)
    cv.imshow('mask', mask)
    # get the color from the original image
    color = cv.bitwise_and(img, img, mask=mask)
    cv.imshow('color', color)
    if cv.waitKey(20) & 0xFF == ord('q'):
        break

cv.waitKey(0)
