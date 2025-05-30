package com.example.projectdummy;

import com.example.projectdummy.account.DocumentMapper;
import com.example.projectdummy.account.model.ContractDocument;
import com.example.projectdummy.account.model.ProductDocumentWithName;
import com.example.projectdummy.productAndDeposit.ProductMapper;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;
import java.util.List;

// 계좌 or 카드 계약시 서류 생성하는 메서드를 담은 클래스
public class AccountDummyDefault extends DummyDefault{
    @Autowired
    DocumentMapper documentMapper;


    public void savingContractDocument(long productId, Long contractId, String productCode, LocalDateTime createdAt) {
        List<ProductDocumentWithName> productDocuments = documentMapper.findProductDocument(productId);
        for(ProductDocumentWithName pd : productDocuments) {
            ContractDocument contractDocument = ContractDocument.builder()
                    .contractId(contractId)
                    .document(pd.getDocumentName()) //나중에 파일명이나 경로로 수정해둘 것.
                    .productDocumentId(pd.getProductDocumentId())
                    .productCode(productCode)
                    .createdAt(createdAt)
                    .build();

            documentMapper.saveContractDocument(contractDocument);
        }
    }
    public boolean isDuplicateKeyException(Exception e) {
        return e.getMessage() != null && e.getMessage().toLowerCase().contains("duplicate");
    }
}
