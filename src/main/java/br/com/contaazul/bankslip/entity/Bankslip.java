package br.com.contaazul.bankslip.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.id.UUIDGenerator;
import org.springframework.data.annotation.Transient;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode
public class Bankslip {

	@Id 
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", 
					strategy = "org.hibernate.id.UUIDGenerator",
					parameters = {
						@Parameter(name = UUIDGenerator.UUID_GEN_STRATEGY_CLASS,
								   value = "org.hibernate.id.uuid.StandardRandomStrategy") 
						})
	private String id;
	
	private LocalDate dueDate;
	
	private BigDecimal totalInCents;
	
	private String customer;
	
	private EnumStatus status;
	
	private LocalDate paymentDate;
	
	@Transient
	private BigDecimal fine = BigDecimal.ZERO;
			
}
