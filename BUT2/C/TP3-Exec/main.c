#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <fcntl.h>

int main(int argc, char *argv[])
{
    if (argc < 8)
    {
        printf(" usage : cmd1 -opt1 arg1 cmd2 -opt2 arg2 file ");
        exit(1);
    }
    int p[2];
    if (pipe(p))
    {
        perror(" pipe ");
        exit(1);
    }
    switch (fork())
    {
    case -1:
        perror(" fork ");
        break;
    case 0:
        int fd;
        if ((fd = open(name, mode, perm)) == -1)
        {
            perror("open");
            exit(2);
        }
        break;
    default:
        break;
    }
    return 0;
}