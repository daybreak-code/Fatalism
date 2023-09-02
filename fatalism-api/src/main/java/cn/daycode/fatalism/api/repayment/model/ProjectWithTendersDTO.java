package cn.daycode.fatalism.api.repayment.model;

import cn.daycode.fatalism.api.transaction.model.ProjectDTO;
import cn.daycode.fatalism.api.transaction.model.TenderDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProjectWithTendersDTO {

    private ProjectDTO project;

    private List<TenderDTO> tenders;

    private BigDecimal commissionInvestorAnnualRate;

    private BigDecimal commissionBorrowerAnnualRate;
}
