export interface IExpenseApprovers {
  id?: number;
  employeeFullname?: string;
  employeeId?: number;
  departmentName?: string;
  departmentId?: number;
  departmentRoleName?: string;
  departmentRoleId?: number;
}

export class ExpenseApprovers implements IExpenseApprovers {
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
