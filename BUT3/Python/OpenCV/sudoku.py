import cv2 as cv
import numpy as np
import tensorflow as tf

SUDOKU_VIDE = 'sudokuDB.png'

pointsDepart = [(111, 219), (287, 188), (352, 440), (154, 482)]
pointsArrivee = [(0, 0), (900, 0), (900, 900), (0, 900)]
input_size = 48

backward = 0
forward = 0
grid = np.zeros((9, 9), dtype=int)
classes = np.arange(0, 10)
model = tf.keras.models.load_model('model-OCR.h5')


def split_box(board):
    assert board.shape[0] == board.shape[1], 'Image must be square'
    assert board.shape[0] % 9 == 0, 'Image size must be divisible by 9'
    assert board.shape[1] % 9 == 0, 'Image size must be divisible by 9'
    rows = np.vsplit(board, 9)
    boxes = []
    for r in rows:
        cols = np.hsplit(r, 9)
        for box in cols:
            box = cv.resize(box, (input_size, input_size)) / 255
            # cv.imshow('Splitted box', box)
            # cv.waitKey(50)
            boxes.append(box)
    return boxes


def detect_document(img):
    # Convert the image to grayscale
    gray = cv.cvtColor(img, cv.COLOR_BGR2GRAY)

    # Blur the image to reduce noise
    blurred = cv.GaussianBlur(gray, (5, 5), 0)

    # Perform edge detection
    edged = cv.Canny(blurred, 30, 180)

    # Find contours in the edge map
    contours, _ = cv.findContours(edged.copy(), cv.RETR_LIST, cv.CHAIN_APPROX_SIMPLE)
    contours = sorted(contours, key=cv.contourArea, reverse=True)[:5]
    biggest = 0
    # Iterate over the contours
    for c in contours:
        # Approximate the contour
        peri = cv.arcLength(c, True)
        approx = cv.approxPolyDP(c, 0.02 * peri, True)
        if len(approx) != 4:
            continue
        cv.drawContours(img, [approx], -1, (0, 255, 0), 2)
        cv.imshow('ImageContr', img)
        if cv.contourArea(c) <= biggest:
            continue
        # If the approximated contour has four points, we have found the document
        biggest = cv.contourArea(c)
        pointsDepart.clear()
        top_left = (10000, 10000)
        top_right = (0, 10000)
        bottom_left = (10000, 0)
        bottom_right = (0, 0)

        for point in approx:
            if point[0][0] + point[0][1] < sum(top_left):
                top_left = (point[0][0], point[0][1])
            if point[0][0] - point[0][1] > top_right[0] - top_right[1]:
                top_right = (point[0][0], point[0][1])
            if point[0][0] + point[0][1] > sum(bottom_right):
                bottom_right = (point[0][0], point[0][1])
            if point[0][0] - point[0][1] < bottom_left[0] - bottom_left[1]:
                bottom_left = (point[0][0], point[0][1])
        pointsDepart.append(top_left)
        pointsDepart.append(top_right)
        pointsDepart.append(bottom_right)
        pointsDepart.append(bottom_left)
    if biggest == 0:
        return False
    return True


if __name__ == '__main__':
    pass


def get_image():
    global grid
    img_original = cv.imread('%s' % SUDOKU_VIDE)
    img = img_original.copy()
    # detect point depart
    detect_document(img.copy())
    # add points to image
    for point in pointsDepart:
        cv.circle(img, point, 5, (0, 0, 255), -1)
    # draw rectangle
    cv.polylines(img, np.array([pointsArrivee]), True, (0, 0, 255), 2)
    # get perspective
    matrix = cv.getPerspectiveTransform(np.float32(pointsDepart), np.float32(pointsArrivee))
    img2 = cv.warpPerspective(img, matrix, (900, 900))
    cv.imshow('Image2', img2)
    gridog = get_grid(img2)
    for i in range(len(gridog)):
        for j in range(len(gridog[i])):
            grid[i][j] = gridog[i][j]
    solve(0, 0)
    sudoku_image = display_number(grid, gridog, img2)

    solved_image(img_original, sudoku_image)


def display_number(grid, gridog, img2):
    sudoku_image = np.zeros_like(img2)
    for i in range(len(grid)):
        for j in range(len(grid[i])):
            if gridog[i][j] == 0:
                coordonate = (j * 100 + input_size//2, (i + 1) * 100 - input_size//2)
                cv.putText(sudoku_image, str(grid[i][j]), coordonate, cv.FONT_HERSHEY_COMPLEX,
                           3, (0, 255, 0), 4)
    return sudoku_image


def solved_image(img_original, sudokuImage):
    # merge with original image
    image_final = img_original.copy()
    # get mask for remove black pixels
    mask = cv.inRange(sudokuImage, (0, 0, 0), (0, 0, 0))
    mask = cv.bitwise_not(mask)
    cv.imshow("mask", mask)
    # remove black pixels
    inverse = cv.bitwise_or(sudokuImage, sudokuImage, mask=mask)
    cv.imshow('Sudoku', inverse)
    # inverse perspective
    matrix = cv.getPerspectiveTransform(np.float32(pointsArrivee), np.float32(pointsDepart))
    inverse = cv.warpPerspective(inverse, matrix, (img_original.shape[1], img_original.shape[0]))
    cv.imshow('Sudoku', inverse)
    # add sudoku to original image
    image_final = cv.add(image_final, inverse)
    cv.imshow('Final', image_final)


def get_grid(img2):
    # split image into 81 boxes
    graysplit = cv.cvtColor(img2, cv.COLOR_BGR2GRAY)
    boxes = split_box(graysplit)
    boxes = np.array(boxes).reshape(-1, input_size, input_size, 1)
    # predict numbers
    predictions = model.predict(boxes)
    predicted_numbers = []
    for i in predictions:
        index = np.argmax(i)
        predicted_numbers.append(classes[index])
    # reshape the list into a 9x9 grid
    grid = np.array(predicted_numbers).astype("uint8").reshape(9, 9)
    return grid


def est_possible(v, i, j):
    global grid
    if grid[i][j] != 0:
        return False

    # check if the number is in the line or the column.
    for k in range(9):
        if grid[i][k] == v or grid[k][j] == v:
            return False

    # check square
    i0 = (i // 3) * 3
    j0 = (j // 3) * 3
    for k in range(3):
        for l in range(3):
            if grid[i0 + k][j0 + l] == v:
                return False
    return True


def next_case(i, j):
    # si on est à la fin de la ligne
    if j < 8:
        return i, j + 1

    # si on est à la fin de la colonne
    elif i < 8:
        return i + 1, 0

    # si on est à la fin de la grille
    else:
        return -1, -1


def solve(i, j):
    global grid
    global backward, forward
    case_suivante = next_case(i, j)
    # if we are at the end of the grid
    if case_suivante[0] == -1:
        for k in range(10):
            if est_possible(k + 1, i, j):
                grid[i][j] = k + 1
        return True

    # if the case is not empty we go to the next case
    if grid[i][j] != 0:
        return solve(case_suivante[0], case_suivante[1])

    # we try all the possible values
    for k in range(9):
        if est_possible(k + 1, i, j):
            grid[i][j] = k + 1
            forward += 1
            if solve(case_suivante[0], case_suivante[1]):
                return True
            backward += 1
            grid[i][j] = 0
    return False


get_image()

while 1:
    if cv.waitKey(20) & 0xFF == ord('q'):
        break
