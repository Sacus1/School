import colorsys
import threading
from collections import deque
import time
import cv2 as cv
pointDepart = (-1, -1)
pointArrivee = (0, 0)


class Point:
    def __init__(self, x, y):
        self.x = x
        self.y = y
        self.parent = None
        self.g = 0
        self.h = 0
        self.f = 0

    def __eq__(self, other):
        return self.x == other.x and self.y == other.y

    def __str__(self):
        return f'({self.x}, {self.y})'

    def __add__(self, other):
        return Point(self.x + other.x, self.y + other.y)

    def __hash__(self):
        return hash((self.x, self.y))


moves = [Point(1, 0), Point(-1, 0), Point(0, 1), Point(0, -1)]



def get_color(t, cycle_length=360 * 10):
    # Normalize t to ensure it loops over the cycle_length for continuous rainbow effect
    # The cycle_length determines how many distinct steps of t form one complete color cycle.
    hue = (t % cycle_length) / float(cycle_length)
    # Set saturation and value to maximum (1.0) for vivid colors
    saturation = 1.0
    value = 1.0
    # Convert HSV to RGB. colorsys returns values in range [0, 1]
    r, g, b = colorsys.hsv_to_rgb(hue, saturation, value)
    # Convert RGB from [0, 1] to [0, 255] for most use cases
    r, g, b = int(r * 255), int(g * 255), int(b * 255)
    return [b, g, r]


def BFS(maze, start, end):
    global img
    time1 = time.time()
    frontier = deque([start])
    reached = {start}
    pas = 0
    while True:
        if len(frontier) == 0:
            return []
        current = frontier.popleft()
        for neighbor in [current + move for move in moves]:
            if not (0 <= neighbor.x < maze.shape[0] and 0 <= neighbor.y < maze.shape[1]):
                continue
            if maze[neighbor.x, neighbor.y] == 0 or neighbor in reached:
                continue
            pas += 1
            img[current.x, current.y] = get_color(pas, 72000)
            if pas % 1000 == 0:
                cv.imshow('image', img)
            # make a rainbow color based on the amount steps
            frontier.append(neighbor)
            reached.add(neighbor)
            neighbor.parent = current
            if neighbor == end:
                print('BFS done in', time.time() - time1)
                return  get_path(neighbor, start)


def AStar(maze, start, end):
    time1 = time.time()
    reached = {start}
    open_set = {start}
    pas = 0
    while True:
        current = min(open_set, key=lambda o: o.f)
        if current == end:
            print('A* done in', time.time() - time1)
            return get_path(current, start)
        open_set.remove(current)
        reached.add(current)
        for neighbor in [current + move for move in moves]:
            if not (0 <= neighbor.x < maze.shape[0] and 0 <= neighbor.y < maze.shape[1]) or maze[neighbor.x,
            neighbor.y] == 0 or neighbor in reached:
                continue
            pas += 1
            img[current.x, current.y] = get_color(current.f, 720)
            if pas % 10000 == 0:
                cv.imshow('image', img)
            if neighbor in open_set:
                if neighbor.g > current.g + 10:
                    neighbor.g = current.g + 10
                    neighbor.parent = current
            else:
                open_set.add(neighbor)
                neighbor.parent = current
                neighbor.g = current.g + 1
                neighbor.h = abs(neighbor.x - end.x) + abs(neighbor.y - end.y)
                neighbor.f = neighbor.g + neighbor.h


def get_path(current, start):
    global img
    path = []
    distance = 0
    while current != start:
        distance += 1
        path.append((current.x, current.y))
        current = current.parent
    print(f'Distance: {distance}')
    draw_path(img, path)


def draw_path(img, path):
    for i in range(len(path) - 1):
        # white line with outline
        cv.line(img, (path[i][1], path[i][0]), (path[i + 1][1], path[i + 1][0]), (0, 0, 0), 3)
    for i in range(len(path) - 1):
        cv.line(img, (path[i][1], path[i][0]), (path[i + 1][1], path[i + 1][0]), (255, 255, 255), 1)
    # redrawing the departure and arrival points
    cv.circle(img, pointDepart, 5, (0, 255, 0), -1)
    cv.circle(img, pointArrivee, 5, (0, 0, 255), -1)
    cv.imshow('image', img)


def onMouseClick(event, x, y, flags, param):
    if event == cv.EVENT_LBUTTONDOWN:
        # set the departure point
        globals().update(pointDepart=(x, y))
    if event == cv.EVENT_RBUTTONDOWN:
        # set the arrival point
        globals().update(pointArrivee=(x, y))


original_image = cv.imread('laby2.png')
img = original_image.copy()
binary = cv.threshold(cv.cvtColor(img, cv.COLOR_BGR2GRAY), 200, 255, cv.THRESH_BINARY)[1]
cv.imshow('image', img)
cv.setMouseCallback('image', onMouseClick)

while 1:
    cv.waitKey(1)
    if pointDepart[0] > 0:
        if pointArrivee[0] > 0:
            break
cv.circle(img, pointDepart, 5, (0, 255, 0), -1)
cv.circle(img, pointArrivee, 5, (0, 0, 255), -1)
start = Point(pointDepart[1], pointDepart[0])
end = Point(pointArrivee[1], pointArrivee[0])
thread = threading.Thread(target=AStar,args=(binary, start, end))
thread.daemon = True
thread.start()

while 1:
    cv.imshow('image', img)
    if cv.waitKey(1) & 0xFF == ord('q'):
        break
