export interface IDepartment {
  id?: number;
  code?: string;
  name?: string;
  description?: string;
  disabled?: boolean;
  notes?: string;
  locationName?: string;
  locationId?: number;
  parentName?: string;
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
    public locationName?: string,
    public locationId?: number,
    public parentName?: string,
    public parentId?: number
  ) {
    this.disabled = this.disabled || false;
  }
}
