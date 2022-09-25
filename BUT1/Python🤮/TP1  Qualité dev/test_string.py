import unittest
import mystring


class TestCaseString(unittest.TestCase):

    def testConcat(self):
        self.assertEqual("Hello World", mystring.strcat("Hello", " World"))


class TestStringOnly(unittest.TestCase):

    def testFirstArg(self):
        self.assertEqual(None, mystring.strcat(1, " World"))

    def testSecondArg(self):
        self.assertEqual(None, mystring.strcat("Hello", 1))


if __name__ == '__main__':
    unittest.main()