import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOvertimeApproval } from 'app/shared/model/overtime-approval.model';

@Component({
  selector: 'jhi-overtime-approval-detail',
  templateUrl: './overtime-approval-detail.component.html'
})
export class OvertimeApprovalDetailComponent implements OnInit {
  overtimeApproval: IOvertimeApproval;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ overtimeApproval }) => {
      this.overtimeApproval = overtimeApproval;
    });
  }

  previousState() {
    window.history.back();
  }
}
