import { Moment } from 'moment';
import { IEmployeeHasRole } from 'app/shared/model/employee-has-role.model';
import { Gender } from 'app/shared/model/enumerations/gender.model';
import { EmployeeStatus } from 'app/shared/model/enumerations/employee-status.model';

export interface IEmployee {
  id?: number;
  code?: string;
  fullname?: string;
  login?: string;
  email?: string;
  personalEmail?: string;
  phoneNumber?: string;
  mobile?: string;
  dateOfJoining?: Moment;
  gender?: Gender;
  status?: EmployeeStatus;
  employeeNumber?: string;
  dateOfBirth?: Moment;
  note?: string;
  userId?: string;
  managerFullname?: string;
  managerId?: number;
  employeeHasRoles?: IEmployeeHasRole[];
}

export class Employee implements IEmployee {
  constructor(
    public id?: number,
    public code?: string,
    public fullname?: string,
    public login?: string,
    public email?: string,
    public personalEmail?: string,
    public phoneNumber?: string,
    public mobile?: string,
    public dateOfJoining?: Moment,
    public gender?: Gender,
    public status?: EmployeeStatus,
    public employeeNumber?: string,
    public dateOfBirth?: Moment,
    public note?: string,
    public userId?: string,
    public managerFullname?: string,
    public managerId?: number,
    public employeeHasRoles?: IEmployeeHasRole[]
  ) {}
}
