export interface IDepartmentRole {
  id?: number;
  code?: string;
  name?: string;
  description?: string;
}

export class DepartmentRole implements IDepartmentRole {
  constructor(public id?: number, public code?: string, public name?: string, public description?: string) {}
}
