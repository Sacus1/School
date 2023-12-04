import cv2 as cv

if __name__ == '__main__':
    pass
# load video from webcam
img = cv.imread("peoples.jpg")
# set size to 800×600
gray = cv.cvtColor(img, cv.COLOR_BGR2GRAY)
haarcascader = cv.CascadeClassifier("haarcascades/haarcascade_frontalface_default.xml")
faces = haarcascader.detectMultiScale(
    gray,
    scaleFactor=1.01,
    minNeighbors=37,
    minSize=(30, 30)
)
for (x, y, w, h) in faces:
    cv.rectangle(img, (x, y), (x + w, y + h), (0, 255, 0), 2)

cv.imshow('frame', img)
while 1 :
    if cv.waitKey(20) & 0xFF == ord('q'):
        break
