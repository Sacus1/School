import cv2 as cv
lowerTolerance = (10, 10, 10)
upperTolerance = (10, 10, 10)
lower = (0, 0, 0)
upper = (180, 255, 255)
cv.namedWindow('imageBinary')
cv.namedWindow('frame')
cv.createTrackbar('H', 'imageBinary', 0, 180, lambda x: globals().update(lowerTolerance=(x, lowerTolerance[1], lowerTolerance[2])))
cv.createTrackbar('S', 'imageBinary', 0, 255, lambda x: globals().update(lowerTolerance=(lowerTolerance[0], x, lowerTolerance[2])))
cv.createTrackbar('V', 'imageBinary', 0, 255, lambda x: globals().update(lowerTolerance=(lowerTolerance[0], lowerTolerance[1], x)))
cv.createTrackbar('H2', 'imageBinary', 180, 180, lambda x: globals().update(upperTolerance=(x, upperTolerance[1], upperTolerance[2])))
cv.createTrackbar('S2', 'imageBinary', 255, 255, lambda x: globals().update(upperTolerance=(upperTolerance[0], x, upperTolerance[2])))
cv.createTrackbar('V2', 'imageBinary', 255, 255, lambda x: globals().update(upperTolerance=(upperTolerance[0], upperTolerance[1], x)))
cv.setMouseCallback('frame', lambda event, x, y, flags, param: globals().update(lower=imgHSV[y, x] - lowerTolerance, upper=imgHSV[y, x] + upperTolerance))
# clamp values
def clamp(value, min, max):
    if value < min:
        return min
    elif value > max:
        return max
    else:
        return value

lower = (clamp(lower[0], 0, 180), clamp(lower[1], 0, 255), clamp(lower[2], 0, 255))
upper = (clamp(upper[0], 0, 180), clamp(upper[1], 0, 255), clamp(upper[2], 0, 255))
# load video from webcam
vid = cv.VideoCapture(0)
# show video
while 1:
    ret, frame = vid.read()
    # convert to HSV
    imgHSV = cv.cvtColor(frame, cv.COLOR_BGR2HSV)
    mask = cv.inRange(imgHSV, lower, upperTolerance)
    color = cv.bitwise_and(frame, frame, mask=mask)
    cv.imshow('color', color)
    cv.imshow('mask', mask)
    cv.imshow('frame', frame)
    cv.imshow('imageHSV', imgHSV)
    if cv.waitKey(20) & 0xFF == ord('q'):
        break
