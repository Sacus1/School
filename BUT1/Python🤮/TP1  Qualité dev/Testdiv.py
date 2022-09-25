import unittest
import div


class TestException(unittest.TestCase):

    def testZeroException(self):
        self.assertRaises(ZeroDivisionError, div.div, 1, 0)

    def testTypeException(self):
        self.assertRaises(TypeError, div.div, 1, "1")


if __name__ == '__main__':
    unittest.main()