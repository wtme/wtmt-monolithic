import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEmployeeDepartment } from 'app/shared/model/employee-department.model';

@Component({
  selector: 'jhi-employee-department-detail',
  templateUrl: './employee-department-detail.component.html'
})
export class EmployeeDepartmentDetailComponent implements OnInit {
  employeeDepartment: IEmployeeDepartment;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ employeeDepartment }) => {
      this.employeeDepartment = employeeDepartment;
    });
  }

  previousState() {
    window.history.back();
  }
}
