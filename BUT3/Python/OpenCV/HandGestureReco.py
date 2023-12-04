'''
This file is used to recognize the hand gesture and return the corresponding
'''
import cv2 as cv

if __name__ == '__main__':
    pass

# load video from webcam
vid = cv.VideoCapture(0)
# set contrast to 150
vid.set(10, 150)
# load cascade
cascade = cv.CascadeClassifier("haarcascades/aGest.xml")
while 1:
    ret, img = vid.read()
    gray = cv.cvtColor(img, cv.COLOR_BGR2GRAY)
    # detect hand gesture
    hand = cascade.detectMultiScale(
        gray,
        scaleFactor=1.1,
        minNeighbors=5,
        minSize=(25, 25)
    )
    # draw rectangle around hand gesture
    for (x, y, w, h) in hand:
        cv.rectangle(img, (x, y), (x + w, y + h), (0, 255, 0), 2)
    cv.imshow('frame', img)
    if cv.waitKey(20) & 0xFF == ord('q'):
        break
