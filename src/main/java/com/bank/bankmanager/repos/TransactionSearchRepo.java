package com.bank.bankmanager.repos;

import com.bank.bankmanager.domain.Transaction;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


@Repository
public class TransactionSearchRepo {

    private final EntityManager entityManager;

    @Autowired
    public TransactionSearchRepo(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    private FullTextQuery getJpaQuery(org.apache.lucene.search.Query luceneQuery) {

        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);

        return fullTextEntityManager.createFullTextQuery(luceneQuery, Transaction.class);
    }

    private QueryBuilder getQueryBuilder() {

        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);

        return fullTextEntityManager.getSearchFactory()
                .buildQueryBuilder()
                .forEntity(Transaction.class)
                .get();
    }

    @Transactional
    public List<Transaction> searchTransactionByWildcardDate(String text) {

        Query wildcardQuery = getQueryBuilder()
                .keyword()
                .wildcard()
                .onField("tstz")
                .matching(text)
                .createQuery();

        List<Transaction> result = getJpaQuery(wildcardQuery).getResultList();

        return result;

    }

}
