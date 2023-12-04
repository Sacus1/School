estime_pi<-function(n){
  x<-runif(n,min=0,max=1)
  y<-runif(n,min=0,max=1)
  sum_sq_xy <- sqrt(x^2 + y^2)
  
  index_in <- which(sum_sq_xy <= 1)
  points_in = length(index_in)
  
  pi_est<-4*points_in/n
  return(pi_est)
}

estime_pi(1000000)

suite_est_pi<-function(n){
  x<-runif(n,min=0,max=1)
  y<-runif(n,min=0,max=1)
  point_in_out<-ifelse(sqrt(x^2+y^2)<=1,1,0)
  suite<-4*cumsum(point_in_out)/(1:n)
  return(suite)
}

n<-10000
plot(1:n,suite_est_pi(n),type="l",xlab="",ylab="Estimation de Pi",main="Evolution de l'estimation de Pi en fonction du nombre d'itérations")
abline(h=pi,col="red")

n<-10000
m<-pi
sigma = 4*sqrt((pi/4)*(1-pi/4))
k = 3
pnorm(m+k*sigma/sqrt(n), m, sigma/sqrt(n)) - pnorm(m-k*sigma/sqrt(n), m, sigma/sqrt(n))

suite_est_pi_gauss<-function(N,n){
  suite<-NULL
  for(i in 1:N){suite <- c(suite,estime_pi(n))}
  return(suite)
}

N<-10000
n<-10000
suite_test<-suite_est_pi_gauss(N,n)

print(length(suite_test[(suite_test>=m-1*sigma/sqrt(n)) & (suite_test<=m+1*sigma/sqrt(n))])/N)
print(length(suite_test[(suite_test>=m-2*sigma/sqrt(n)) & (suite_test<=m+2*sigma/sqrt(n))])/N)
print(length(suite_test[(suite_test>=m-3*sigma/sqrt(n)) & (suite_test<=m+3*sigma/sqrt(n))])/N)
print(length(suite_test[(suite_test<m-3*sigma/sqrt(n)) | (suite_test>m+3*sigma/sqrt(n))])/N)


hist(suite_test, freq=F, ylab="Densité (aire totale=1)", col="papayawhip", main="Estimation de Pi", las=1)
f=function(x) (1/sqrt(2*pi)/(sigma/sqrt(n)) * exp(-(x-m)^2/2/(sigma/sqrt(n))^2))

m<-pi
sigma<-4*sqrt(pi/4*(1-pi/4))
curve(dnorm(x,mean=m,sd=sigma/sqrt(n)),ylab="densité",add=T,col="darkblue")

m1 <- mean(suite_test)
sigma1<-sqrt(mean((suite_test-m1)^2))

print(m1)
print(sigma1)