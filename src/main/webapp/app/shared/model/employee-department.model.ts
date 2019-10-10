export interface IEmployeeDepartment {
  id?: number;
  employeeFullname?: string;
  employeeId?: number;
  departmentName?: string;
  departmentId?: number;
}

export class EmployeeDepartment implements IEmployeeDepartment {
  constructor(
    public id?: number,
    public employeeFullname?: string,
    public employeeId?: number,
    public departmentName?: string,
    public departmentId?: number
  ) {}
}
