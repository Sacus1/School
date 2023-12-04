import cv2 as cv


def mouseCallback(event, x, y, flags, param):
    if event == cv.EVENT_LBUTTONDOWN:
        globals().update(isWriting=not isWriting)
        # add empty point
        if not isWriting:
            points.append((0, 0))
def clear_points():
    points.clear()

def undo_last_point():
    points.pop(len(points) - 1)
    for i in range(len(points) - 1, -1, -1):
        if points[i][0] < 1 and points[i][1] < 1:
            print("break at", i,"out of", len(points), "points : ", points[i])
            break
        points.pop(i) 
def exit_program():
    exit(0)
isWriting = False
lowerTolerance = (49, 100, 121)
upperTolerance = (180, 255, 227)
cv.namedWindow('imageBinary')
cv.namedWindow('frame')
cv.namedWindow('paint')
cv.createTrackbar('H', 'imageBinary', lowerTolerance[0], 180,
                  lambda x: globals().update(lowerTolerance=(x, lowerTolerance[1], lowerTolerance[2])))
cv.createTrackbar('S', 'imageBinary', lowerTolerance[1], 255,
                  lambda x: globals().update(lowerTolerance=(lowerTolerance[0], x, lowerTolerance[2])))
cv.createTrackbar('V', 'imageBinary', lowerTolerance[2], 255,
                  lambda x: globals().update(lowerTolerance=(lowerTolerance[0], lowerTolerance[1], x)))
cv.createTrackbar('H2', 'imageBinary', upperTolerance[0], 180, lambda x: globals().update(upperTolerance=(x,
                                                                                                          upperTolerance[
                                                                                                              1],
                                                                                                          upperTolerance[
                                                                                                              2])))
cv.createTrackbar('S2', 'imageBinary', upperTolerance[1], 255, lambda x: globals().update(upperTolerance=(
    upperTolerance[0], x,
    upperTolerance[2])))
cv.createTrackbar('V2', 'imageBinary', upperTolerance[2], 255, lambda x: globals().update(upperTolerance=(
    upperTolerance[0],
    upperTolerance[1], x)))
cv.setMouseCallback('frame', mouseCallback)
cv.setMouseCallback('paint', mouseCallback)

# load video from webcam
vid = cv.VideoCapture(0)
# set contrast to 150
vid.set(10, 150)
if __name__ == '__main__':
    pass
points = []
# show video
while 1:
    ret, img = vid.read()
    paint = img.copy()
    # convert to HSV
    imgHSV = cv.cvtColor(img, cv.COLOR_BGR2HSV)
    mask = cv.inRange(imgHSV, lowerTolerance, upperTolerance)
    color = cv.bitwise_and(imgHSV, imgHSV, mask=mask)
    # grey
    imgGrey = cv.cvtColor(color, cv.COLOR_BGR2GRAY)
    # image lisser
    imgBlur2 = cv.GaussianBlur(imgGrey, (7, 7), 1)
    # edge detection
    imgCanny2 = cv.Canny(imgBlur2, 50, 50)
    # boîtes englobantes
    contours, hierarchy = cv.findContours(imgCanny2, cv.RETR_EXTERNAL, cv.CHAIN_APPROX_NONE)
    # draw contours
    biggest = None
    # dictionary
    for contour in contours:
        if cv.contourArea(contour) < 500:
            continue
        # get the biggest contour
        if biggest is None or cv.contourArea(contour) > cv.contourArea(biggest):
            biggest = contour
    # draw biggest contour
    colorDot = (255, 0, 0)

    if biggest is not None:
        x, y, w, h = cv.boundingRect(biggest)
        if x == 0 and y == 0 and w == 0 and h == 0:
            continue
        xCenter = x + w // 2
        yCenter = y + h // 2
        # draw points
        if isWriting:
            points.append((xCenter, yCenter))

    # make line between points
    for i in range(1, len(points)):
        if points[i - 1][0] == 0 and points[i - 1][1] == 0:
            continue
        if points[i][0] == 0 and points[i][1] == 0:
            continue
        cv.line(paint, points[i - 1], points[i],color=colorDot, thickness=2)

    # flip image
    img = cv.flip(img, 1)
    paint = cv.flip(paint, 1)

    cv.imshow('color', color)
    #cv.imshow('frame', img)
    cv.imshow('paint', paint)
    key = cv.waitKey(20) & 0xFF
    actions = {
        ord('c'): clear_points,
        ord('z'): undo_last_point,
        ord('q'): exit_program
    }

    if key in actions:
        actions[key]()
