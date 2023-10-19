import numpy as np
import cv2 as cv

isRightClick = False
radius = 1


# mouse callback function
def draw_circle(event, x, y, flags, param):
    global isRightClick, radius
    if event == cv.EVENT_LBUTTONDOWN:
        isRightClick = True
    elif event == cv.EVENT_LBUTTONUP:
        isRightClick = False
    if event == cv.EVENT_MOUSEMOVE and isRightClick:
        cv.circle(img, (x, y), radius, color=color, thickness=-1)
        print(radius, color)


# Create a black image, a window and bind the function to window
img = np.zeros((512, 512, 3), np.uint8)
cv.namedWindow('image')
cv.setMouseCallback('image', draw_circle)
# radius
cv.createTrackbar('Radius', 'image', 1, 100, lambda x: globals().update(radius=x))
# color
cv.createTrackbar('R', 'image', 255, 255, lambda x : x)
cv.createTrackbar('G', 'image', 0, 255, lambda x : x)
cv.createTrackbar('B', 'image', 0, 255, lambda x : x)
while 1:
    cv.imshow('image', img)
    # color
    color = (
        cv.getTrackbarPos('B', 'image'),
        cv.getTrackbarPos('G', 'image'),
        cv.getTrackbarPos('R', 'image')
    )

    if cv.waitKey(20) & 0xFF == ord('q'):
        break
cv.destroyAllWindows()
