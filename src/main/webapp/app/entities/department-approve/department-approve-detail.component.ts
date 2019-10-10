import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDepartmentApprove } from 'app/shared/model/department-approve.model';

@Component({
  selector: 'jhi-department-approve-detail',
  templateUrl: './department-approve-detail.component.html'
})
export class DepartmentApproveDetailComponent implements OnInit {
  departmentApprove: IDepartmentApprove;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ departmentApprove }) => {
      this.departmentApprove = departmentApprove;
    });
  }

  previousState() {
    window.history.back();
  }
}
