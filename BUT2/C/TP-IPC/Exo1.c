#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>
// include shm
#include <sys/shm.h>
// include signal
#include <signal.h>
#include <string.h>
void signal_read(int sig)
{
    printf("Signal recu : %d\n", sig);
}
void lecture(int shmid)
{
    // assignation du signal
    signal(SIGUSR1, signal_read);
    pause();
    char *ptr = shmat(shmid, NULL, 0);
    printf("Lecture : %s\n", ptr);
    shmdt(ptr);
}
void ecriture(int shmid, char *val)
{
    char *ptr = shmat(shmid, NULL, 0);
    strcpy(ptr, val);
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
    switch (pid=fork())
    {
    case -1:
        perror("fork");
        exit(1);
    case 0:
        lecture(shmid);
        break;
    default:
        ecriture(shmid, "salut");
        sleep(2);
        // get child pid and send signal
        kill(pid, SIGUSR1);
        break;
    }
}
