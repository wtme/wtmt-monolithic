export interface IDepartmentApprove {
  id?: number;
  employeeFullname?: string;
  employeeId?: number;
  departmentName?: string;
  departmentId?: number;
  departmentRoleName?: string;
  departmentRoleId?: number;
}

export class DepartmentApprove implements IDepartmentApprove {
  constructor(
    public id?: number,
    public employeeFullname?: string,
    public employeeId?: number,
    public departmentName?: string,
    public departmentId?: number,
    public departmentRoleName?: string,
    public departmentRoleId?: number
  ) {}
}
