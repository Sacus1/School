#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <sys/wait.h>
// include shm
#include <sys/shm.h>
// include signal
#include <signal.h>
char strfather[10] = "salut";
char strchild[10] = "bonjour";
void signal_read_fils(int sig)
{
    printf("Signal recu : %d", sig);
    int shmid = shmget(0x75, 4096, IPC_CREAT | 0777);
    if (shmid == -1)
    {
        perror("shmget");
        exit(1);
    }
    char *ptr = shmat(shmid, NULL, 0);
    printf("Lecture fils : %s\n", ptr);
    sprintf(ptr, "%s", strfather);
    kill(getppid(), SIGUSR2);
    shmdt(ptr);
}

void signal_read_pere(int sig)
{
    printf("Signal recu : %d", sig);
    int shmid = shmget(0x75, 4096, IPC_CREAT | 0777);
    if (shmid == -1)
    {
        perror("shmget");
        exit(1);
    }
    char *ptr = shmat(shmid, NULL, 0);
    printf("Lecture pere : %s\n", ptr);
    shmdt(ptr);
}

int main(int argc, char *argv[])
{
    int shmid = shmget(0x75, 4096, IPC_CREAT | 0777);
    if (shmid == -1)
    {
        perror("shmget");
        exit(1);
    }
    int pid;
    switch (pid = fork())
    {
    case -1:
        perror("fork");
        exit(1);
    case 0:
        signal(SIGUSR1, signal_read_fils);
        pause();
        break;
    default:
        sleep(2);
        signal(SIGUSR2, signal_read_pere);
        sprintf(shmat(shmid, NULL, 0), "%s", strchild);
        kill(pid, SIGUSR1);
        pause();
        break;
    }
}
