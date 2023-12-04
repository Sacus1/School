# EXERCICE 1

#1.a)

alpha <- 0.05
mu0 <- 1
sigma <- 3
n <- 30
xs <- qnorm(1-alpha, mu0, sigma/sqrt(n)) #qnorm : quantile loi normale
print(paste("seuil xs:", xs))
print(paste("Region de rejet R: [", xs, ", +inf["))

#1.b)
mu1 <- 1.5
beta <- pnorm(xs, mu1, sigma/sqrt(n)) #pnorm : fonction de repartition loi normale
print(paste("beta: ", beta))

#1.c)
pi <- 1 - beta
print(paste("pi: ", pi))

#2.a)
echant <- read.table("echantillon04.txt", sep="\t", header=TRUE)
print(echant)
print(echant$x)
xbar <- mean(echant$x)
print(paste("x barre:", xbar))

#2.b)
print(paste("x barre appartient a R?", xbar >= xs))
# conclusion: H0 n'est pas rejetee

#2.c)
pvalue <- 1 - pnorm(xbar, mu0, sigma/sqrt(n))
print(paste("pvalue: ", pvalue))

#2.d)
print(paste("test significatif a 5%?", pvalue <= 0.05))
# conclusion: test non significatif (pvalue > 5%)

#2.e)
print(paste("test significatif a 1%?", pvalue <= 0.01))
# conclusion: test non significatif a 1% (pvalue > 1%)


# EXERCICE 2
mu1<-1.65
abscn<-c(20,30,40,50,100,150,200,250,300,350)
ordpuis<-NULL
for(i in abscn){
  xs<-qnorm(1-alpha,mu0,sigma/sqrt(i))
  beta<-pnorm(xs,mu1,sigma/sqrt(i))
  ordpuis<-c(ordpuis,1-beta)
  print(c(i,xs,beta,1-beta))
}
plot(abscn,ordpuis,main="Puissance en fonction de n")
lines(abscn, ordpuis, col="red")

n=30
absca<-seq(0.01, 0.1, 0.01)
print(absca)
ordpuis<-NULL
for(i in absca){
  xs<-qnorm(1-i,mu0,sigma/sqrt(n))
  beta<-pnorm(xs,mu1,sigma/sqrt(n))
  ordpuis<-c(ordpuis,1-beta)
  print(c(i,xs,beta,1-beta))
}
plot(absca,ordpuis,main="Puissance en fonction de alpha")
lines(absca, ordpuis, col="red")

n=30
alpha=0.05
abscmu<-seq(1.5, 2.5, 0.1)
print(abscmu)
ordpuis<-NULL
for(i in abscmu){
  xs<-qnorm(1-alpha,mu0,sigma/sqrt(n))
  beta<-pnorm(xs,i,sigma/sqrt(n))
  ordpuis<-c(ordpuis,1-beta)
  print(c(i,xs,beta,1-beta))
}
plot(abscmu,ordpuis,main="Puissance en fonction de mu1")
lines(abscmu, ordpuis, col="red")

# EXERCICE 3

xi<-seq(0,12,1)
ni<-c(4,18,33,49,41,33,19,16,8,6,6,4,3)
print(xi)
print(ni)
n<-sum(ni)
lambda<-sum(xi*ni)/n
print(lambda)

thi<-n*dpois(xi,lambda)
print(thi)

ni<- c(ni[1] + ni[2], ni[3], ni[4], ni[5], ni[6], ni[7], ni[8], ni[9], ni[10] + ni[11] + ni[12] + ni[13])
thi<- c(thi[1] + thi[2], thi[3], thi[4], thi[5], thi[6], thi[7], thi[8], thi[9], thi[10] + thi[11] + thi[12] + thi[13])
print(ni)
print(thi)

d<-sum((ni-thi)^2/thi)
print(d)

nu = 7
delta<-qchisq(0.95,df=nu)
print(delta)

pvalue<-1 - pchisq(d, df=nu)
print(pvalue)