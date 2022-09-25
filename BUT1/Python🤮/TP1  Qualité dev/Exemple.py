import unittest


class TestStringMethods(unittest.TestCase):  #Creation classe fille de TestCase

    def test_upper(self):  # test individuel
        self.assertEqual('foo'.upper(),
                         'FOO')  #Est ce que foo en majuscule est egal a FOO

    def test_isupper(self):
        self.assertTrue('FOO'.isupper())  #Failed si FOO n'est pas en majuscule
        self.assertFalse('Foo'.isupper())  #Failed si Foo est en majuscule

    def test_split(self):
        s = 'hello world'
        self.assertEqual(s.split(), [
            'hello', 'world'
        ])  #On verifie si la table des mot de s est egal a ['hello','world']
        # check that s.split fails when the separator is not a string
        with self.assertRaises(TypeError):
            s.split(2)


if __name__ == '__main__':
    unittest.main()