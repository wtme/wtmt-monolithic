import { Moment } from 'moment';
import { IOvertimeApproval } from 'app/shared/model/overtime-approval.model';
import { IOvertimeHistory } from 'app/shared/model/overtime-history.model';
import { IOvertimeComment } from 'app/shared/model/overtime-comment.model';
import { OvertimeStatus } from 'app/shared/model/enumerations/overtime-status.model';

export interface IOvertime {
  id?: number;
  code?: string;
  name?: string;
  status?: OvertimeStatus;
  details?: string;
  fromDate?: Moment;
  toDate?: Moment;
  createdDate?: Moment;
  overtimeInHours?: number;
  totalBillableHours?: number;
  totalCostingAmount?: number;
  note?: string;
  location?: string;
  employeeName?: string;
  employeeEmail?: string;
  departmentName?: string;
  departmentCode?: string;
  overtimeApprovals?: IOvertimeApproval[];
  overtimeHistories?: IOvertimeHistory[];
  overtimeComments?: IOvertimeComment[];
}

export class Overtime implements IOvertime {
  constructor(
    public id?: number,
    public code?: string,
    public name?: string,
    public status?: OvertimeStatus,
    public details?: string,
    public fromDate?: Moment,
    public toDate?: Moment,
    public createdDate?: Moment,
    public overtimeInHours?: number,
    public totalBillableHours?: number,
    public totalCostingAmount?: number,
    public note?: string,
    public location?: string,
    public employeeName?: string,
    public employeeEmail?: string,
    public departmentName?: string,
    public departmentCode?: string,
    public overtimeApprovals?: IOvertimeApproval[],
    public overtimeHistories?: IOvertimeHistory[],
    public overtimeComments?: IOvertimeComment[]
  ) {}
}
