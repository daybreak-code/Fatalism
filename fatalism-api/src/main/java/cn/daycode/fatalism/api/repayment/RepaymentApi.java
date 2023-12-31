package cn.daycode.fatalism.api.repayment;

import cn.daycode.fatalism.api.repayment.model.ProjectWithTendersDTO;
import cn.daycode.fatalism.common.domain.RestResponse;

public interface RepaymentApi {

    RestResponse<String> startRepayment(ProjectWithTendersDTO projectWithTendersDTO);

}
