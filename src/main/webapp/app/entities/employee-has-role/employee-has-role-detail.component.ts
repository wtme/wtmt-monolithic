import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEmployeeHasRole } from 'app/shared/model/employee-has-role.model';

@Component({
  selector: 'jhi-employee-has-role-detail',
  templateUrl: './employee-has-role-detail.component.html'
})
export class EmployeeHasRoleDetailComponent implements OnInit {
  employeeHasRole: IEmployeeHasRole;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ employeeHasRole }) => {
      this.employeeHasRole = employeeHasRole;
    });
  }

  previousState() {
    window.history.back();
  }
}
