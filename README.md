### [수업중 2-5] SpringBoot와 JPA 활용
* Student와 StudentDetail 1:1(OneToOne) 연관관계
* Student와 Department 1:N (OneToMany) 연관관계
    * FetchType.LAZY vs FetchType.EAGER
    * @JoinColumn, mappedBy
    * 연관관계의 주인(owner)와 종속(non-owner)
        * Owner(BookDetail,Non-Owner(Book))
* DTO
* Controller
* Service
* Repository
* DataInsertRunner
* N+1 문제 해결
  * 성능 개선 : 
  * HibernateModule 사용하여 BatchSize 설정하기