export interface IOvertimeApproval {
  id?: number;
  approverName?: string;
  approverEmail?: string;
  overtimeName?: string;
  overtimeId?: number;
}

export class OvertimeApproval implements IOvertimeApproval {
  constructor(
    public id?: number,
    public approverName?: string,
    public approverEmail?: string,
    public overtimeName?: string,
    public overtimeId?: number
  ) {}
}
