import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IDepartmentRole, DepartmentRole } from 'app/shared/model/department-role.model';
import { DepartmentRoleService } from './department-role.service';

@Component({
  selector: 'jhi-department-role-update',
  templateUrl: './department-role-update.component.html'
})
export class DepartmentRoleUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required, Validators.maxLength(50)]],
    name: [null, [Validators.required, Validators.maxLength(100)]],
    description: []
  });

  constructor(protected departmentRoleService: DepartmentRoleService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ departmentRole }) => {
      this.updateForm(departmentRole);
    });
  }

  updateForm(departmentRole: IDepartmentRole) {
    this.editForm.patchValue({
      id: departmentRole.id,
      code: departmentRole.code,
      name: departmentRole.name,
      description: departmentRole.description
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const departmentRole = this.createFromForm();
    if (departmentRole.id !== undefined) {
      this.subscribeToSaveResponse(this.departmentRoleService.update(departmentRole));
    } else {
      this.subscribeToSaveResponse(this.departmentRoleService.create(departmentRole));
    }
  }

  private createFromForm(): IDepartmentRole {
    return {
      ...new DepartmentRole(),
      id: this.editForm.get(['id']).value,
      code: this.editForm.get(['code']).value,
      name: this.editForm.get(['name']).value,
      description: this.editForm.get(['description']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDepartmentRole>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
