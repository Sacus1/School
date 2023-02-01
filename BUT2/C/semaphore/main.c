#include <pthread.h>
#include <semaphore.h>
#include <stdio.h>
#include <stdlib.h>
#include <semaphore.h>
#define N 1000000
int compteur = 0;
void * incr(void * a)
{
    int i, tmp;
    // init semaphore
    sem_t *sem = (sem_t*) a;
    sem_wait(sem);
    for (i = 0; i < N; i++)
    {
        tmp = compteur;
        tmp = tmp + 1;
        compteur = tmp;
    }
    sem_post(sem);
}
int main(int argc, char * argv[])
{
    pthread_t tid1, tid2;
    sem_t* sem = (sem_t*) malloc(sizeof(sem_t));
    sem_init(sem, 0, 1);
    if(pthread_create(&tid1, NULL, incr, sem))
    {
        printf("\n ERREUR création thread 1");
        exit(1);
    }
    if(pthread_create(&tid2, NULL, incr, sem))
    {
        printf("\n ERROR création thread 2");
        exit(1);
    }
    if(pthread_join(tid1, NULL)) /* Attente de fin de thread 1 */
    {
        printf("\n ERREUR thread 1 ");
        exit(1);
    }
    if(pthread_join(tid2, NULL)) /* Attente de fin de thread 2 */
    {
        printf("\n ERREUR thread 2");
        exit(1);
    }
    if ( compteur < 2 * N)
        printf("\n BOOM! compteur = [%d], devrait être %d\n", compteur, 2*N);
    else
        printf("\n OK! compteur = [%d]\n", compteur);
    sem_destroy(sem);
    pthread_exit(NULL);
    return 0;
}
