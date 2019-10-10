import { Moment } from 'moment';
import { OvertimeStatus } from 'app/shared/model/enumerations/overtime-status.model';

export interface IOvertimeHistory {
  id?: number;
  date?: Moment;
  status?: OvertimeStatus;
  changeType?: string;
  who?: string;
  overtimeId?: number;
}

export class OvertimeHistory implements IOvertimeHistory {
  constructor(
    public id?: number,
    public date?: Moment,
    public status?: OvertimeStatus,
    public changeType?: string,
    public who?: string,
    public overtimeId?: number
  ) {}
}
