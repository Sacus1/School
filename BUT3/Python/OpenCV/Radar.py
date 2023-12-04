import cv2 as cv
import numpy as np
'''
This program is used to detect license plates on cars and isolate them in a new image
'''
if __name__ == '__main__':
    pass

vid = cv.VideoCapture(0)
vid.set(10, 150)
# load cascade
cascade = cv.CascadeClassifier("haarcascades/haarcascade_russian_plate_number.xml")
plates = []
while 1:
    # close all windows
    ret, img = vid.read()
    gray = cv.cvtColor(img, cv.COLOR_BGR2GRAY)
    # detect license plate
    license_plate = cascade.detectMultiScale(
        gray,
        scaleFactor=1.1,
        minNeighbors=5,
        minSize=(25, 25)
    )
    # draw rectangle around license plate
    for (x, y, w, h) in license_plate:
        pts1 = np.float32([(x, y), (x + w, y), (x, y + h), (x + w, y + h)])
        pts2 = np.float32([(0, 0), (200, 0), (0, 200), (200, 200)])
        matrix = cv.getPerspectiveTransform(pts1, pts2)
        plate = cv.warpPerspective(img, matrix, (200, 200))
        # get 10 first frames
        if len(plates) < 10:
            plates.append(plate)
        else:
            plates.pop(0)
            plates.append(plate)
        cv.imshow('plate', plate)
        cv.rectangle(img, (x, y), (x + w, y + h), (0, 255, 0), 2)

    cv.imshow('frame', img)


    def save_image():
        print('Saving image')
        if len(license_plate) == 1:
            # save the average of the 10 first frames
            cv.imwrite('plate.png', np.average(plates, axis=0))
            print('Image saved')


    def quit_program():
        return 'break'


    # Create a dictionary to act as a switch
    switch = {
        's': save_image,
        'q': quit_program
    }

    # Use the switch
    key = chr(cv.waitKey(20) & 0xFF)
    if key in switch:
        result = switch[key]()
        if result == 'break':
            break
