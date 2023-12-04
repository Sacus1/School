import cv2 as cv

img = cv.imread('shapes.png')
# grey
imgGrey = cv.cvtColor(img, cv.COLOR_BGR2GRAY)
# image lisser
imgBlur = cv.GaussianBlur(imgGrey, (7, 7), 1)
# edge detection
imgCanny = cv.Canny(imgBlur, 50, 50)
# boîtes englobantes
contours, hierarchy = cv.findContours(imgCanny, cv.RETR_EXTERNAL, cv.CHAIN_APPROX_NONE)
# draw contours
imgBox = img.copy()
imgLabel = img.copy()
imgContour = img.copy()
# dictionary
d = {3: "triangle", 5: "pentagon", 6: "hexagon", 7: "heptagon", 8: "circle"}
for contour in contours:
    cv.drawContours(imgContour, contour, -1, (255, 0, 0), 3)
    # find size of the box
    peri = cv.arcLength(contour, True)
    # draw box
    x, y, w, h = cv.boundingRect(contour)
    cv.rectangle(imgBox, (x, y), (x + w, y + h), (0, 255, 0), 2)
    # find amount corners
    approx = cv.approxPolyDP(contour, 0.02 * peri, True)
    # draw label
    if len(approx) == 4:
        # difference between square and rectangle
        width, height = approx[0][0][0] - approx[1][0][0], approx[0][0][1] - approx[1][0][1]
        ratio = width / height
        if 0.95 < ratio < 1.05:
            label = "square"
        else:
            label = "rectangle"
    else:
        label = d.get(len(approx))
    cv.putText(imgLabel, label, (x, y + h // 2), cv.FONT_HERSHEY_COMPLEX, 0.5, (0, 0, 0), 1)

# show images
cv.imshow('shapes', img)
cv.waitKey(0)
cv.imshow('shapesGrey', imgGrey)
cv.waitKey(0)
cv.imshow('shapesBlur', imgBlur)
cv.waitKey(0)
cv.imshow('shapesCanny', imgCanny)
cv.waitKey(0)
cv.imshow('shapesContour', imgContour)
cv.waitKey(0)
cv.imshow('shapesBox', imgBox)
cv.waitKey(0)
cv.imshow('shapesLabel', imgLabel)
cv.waitKey(0)
