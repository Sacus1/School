#Exercice 1

freq_gain_maintien<-function(n){
  S_gain_maintien<-0
  for(i in 1:n){
    #x0 numéro porte voiture
    x0<-sample(c(1,2,3),1)
    #x1 choix candidat
    x1<-sample(c(1,2,3),1)
    #x2 choix animateur différent de x0 et x1
    d<-setdiff(c(1,2,3),c(x0,x1))
    if(length(d) == 1) x2<-d else x2<-sample(d,1)
    gain_maintien<-ifelse(x0==x1,1,0)
    #Remarque : si l'ensemble d a un seul élément x, l'échantilon retourné est l'intervalle 1:x
    #gain_maintien = 1 si gain par maintien de la porte choisie, 0 sinon
    S_gain_maintien<-S_gain_maintien+gain_maintien
  }
  return(S_gain_maintien/n)
}

suite_freq_gain_maintien<-function(n){
  #somme des gains si maintient
  suite_gain_maintien<-NULL
  S_gain_maintien<-0
  for(i in 1:n){
    #x0 numéro porte voiture
    x0<-sample(c(1,2,3),1)
    #x1 choix candidat
    x1<-sample(c(1,2,3),1)
    #x2 choix animateur différent de x0 et x1
    d<-setdiff(c(1,2,3),c(x0,x1))
    if(length(d) == 1) x2<-d else x2<-sample(d,1)
    gain_maintien<-ifelse(x0==x1,1,0)
    S_gain_maintien<-S_gain_maintien+gain_maintien
    suite_gain_maintien<-c(suite_gain_maintien,S_gain_maintien/i)
  }
  return(suite_gain_maintien)
}

n<-1000
plot(1:n,suite_freq_gain_maintien(n),type="l",xlab="",ylab="Fréquence de gain par maintien")
abline(h=1/3,col="red")

# Partie 2

#Xi suit la loi de bernoulli avec p=1/3

n<-10000
m<-1/3
sigma = 2/9
k = 3
pnorm(m+k*sigma/sqrt(n), m, sigma/sqrt(n)) - pnorm(m-k*sigma/sqrt(n), m, sigma/sqrt(n))

suite_freq_gain_maintien_gauss<-function(N,n){
  suite<-NULL
  for(i in 1:N){suite <- c(suite,freq_gain_maintien(n))}
  return(suite)
}

N<-100
n<-1000
suite_test<-suite_freq_gain_maintien_gauss(N,n)

print(length(suite_test[(suite_test>=m-1*sigma/sqrt(n)) & (suite_test<=m+1*sigma/sqrt(n))])/N)
print(length(suite_test[(suite_test>=m-2*sigma/sqrt(n)) & (suite_test<=m+2*sigma/sqrt(n))])/N)
print(length(suite_test[(suite_test>=m-3*sigma/sqrt(n)) & (suite_test<=m+3*sigma/sqrt(n))])/N)
print(length(suite_test[(suite_test<m-3*sigma/sqrt(n)) | (suite_test>m+3*sigma/sqrt(n))])/N)

hist(suite_test, freq=F, ylab="Densité (aire totale=1)", col="papayawhip", main="Fréquence de gain avec maintien", las=1)
m<-1/3
sigma<-1/3*2/3
f=function(x) (1/sqrt(2*pi)/(sigma/sqrt(n)) * exp(-(x-m)^2/2/(sigma/sqrt(n))^2))
curve(f, xlim=c(0.28,0.38),ylim=c(0,100),col="darkblue",lwd=2,add=T)

m1<-mean(suite_test)
sigma1<-sqrt(mean((suite_test-m1)^2))
print(m1)
print(sigma1)