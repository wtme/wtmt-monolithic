export interface ILocation {
  id?: number;
  code?: string;
  name?: string;
  streetAddress?: string;
  postalCode?: string;
  city?: string;
  stateProvince?: string;
}

export class Location implements ILocation {
  constructor(
    public id?: number,
    public code?: string,
    public name?: string,
    public streetAddress?: string,
    public postalCode?: string,
    public city?: string,
    public stateProvince?: string
  ) {}
}
