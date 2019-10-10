import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDepartmentRole } from 'app/shared/model/department-role.model';

@Component({
  selector: 'jhi-department-role-detail',
  templateUrl: './department-role-detail.component.html'
})
export class DepartmentRoleDetailComponent implements OnInit {
  departmentRole: IDepartmentRole;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ departmentRole }) => {
      this.departmentRole = departmentRole;
    });
  }

  previousState() {
    window.history.back();
  }
}
