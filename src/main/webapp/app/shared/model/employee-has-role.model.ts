export interface IEmployeeHasRole {
  id?: number;
  name?: string;
  employeeId?: number;
}

export class EmployeeHasRole implements IEmployeeHasRole {
  constructor(public id?: number, public name?: string, public employeeId?: number) {}
}
