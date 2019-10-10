import { Moment } from 'moment';
import { IEmployee } from 'app/shared/model/employee.model';

export interface IDepartment {
  id?: number;
  code?: string;
  name?: string;
  description?: string;
  disabled?: boolean;
  notes?: string;
  startDate?: Moment;
  endDate?: Moment;
  locationId?: number;
  employees?: IEmployee[];
  parentId?: number;
}

export class Department implements IDepartment {
  constructor(
    public id?: number,
    public code?: string,
    public name?: string,
    public description?: string,
    public disabled?: boolean,
    public notes?: string,
    public startDate?: Moment,
    public endDate?: Moment,
    public locationId?: number,
    public employees?: IEmployee[],
    public parentId?: number
  ) {
    this.disabled = this.disabled || false;
  }
}
