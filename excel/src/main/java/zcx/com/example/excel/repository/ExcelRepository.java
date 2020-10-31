package zcx.com.example.excel.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import zcx.com.example.excel.entity.ExcelEntity;

public interface ExcelRepository extends JpaRepository<ExcelEntity,Long> {

}
