import { Moment } from 'moment';

export interface IOvertimeComment {
  id?: number;
  comment?: string;
  createdDate?: Moment;
  employeeName?: string;
  employeeEmail?: string;
  overtimeId?: number;
}

export class OvertimeComment implements IOvertimeComment {
  constructor(
    public id?: number,
    public comment?: string,
    public createdDate?: Moment,
    public employeeName?: string,
    public employeeEmail?: string,
    public overtimeId?: number
  ) {}
}
