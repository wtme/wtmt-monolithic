import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'location',
        loadChildren: () => import('./location/location.module').then(m => m.WmteLocationModule)
      },
      {
        path: 'department',
        loadChildren: () => import('./department/department.module').then(m => m.WmteDepartmentModule)
      },
      {
        path: 'department-approve',
        loadChildren: () => import('./department-approve/department-approve.module').then(m => m.WmteDepartmentApproveModule)
      },
      {
        path: 'expense-approvers',
        loadChildren: () => import('./expense-approvers/expense-approvers.module').then(m => m.WmteExpenseApproversModule)
      },
      {
        path: 'employee',
        loadChildren: () => import('./employee/employee.module').then(m => m.WmteEmployeeModule)
      },
      {
        path: 'employee-has-role',
        loadChildren: () => import('./employee-has-role/employee-has-role.module').then(m => m.WmteEmployeeHasRoleModule)
      },
      {
        path: 'department-role',
        loadChildren: () => import('./department-role/department-role.module').then(m => m.WmteDepartmentRoleModule)
      },
      {
        path: 'overtime-approval',
        loadChildren: () => import('./overtime-approval/overtime-approval.module').then(m => m.WmteOvertimeApprovalModule)
      },
      {
        path: 'overtime',
        loadChildren: () => import('./overtime/overtime.module').then(m => m.WmteOvertimeModule)
      },
      {
        path: 'overtime-comment',
        loadChildren: () => import('./overtime-comment/overtime-comment.module').then(m => m.WmteOvertimeCommentModule)
      },
      {
        path: 'overtime-history',
        loadChildren: () => import('./overtime-history/overtime-history.module').then(m => m.WmteOvertimeHistoryModule)
      },
      {
        path: 'employee-department',
        loadChildren: () => import('./employee-department/employee-department.module').then(m => m.WmteEmployeeDepartmentModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class WmteEntityModule {}
