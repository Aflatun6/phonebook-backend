package az.phonebook.backend.utility;

public class SqlQueries {

    public static final String GET_CURRENCY_RATE_BY_RATE_TYPE_CURRENCY_BRANCH = "SELECT \n"
            + " T.BRANCH_CODE,\n"
            + " T.RATE_TYPE,\n"
            + " T.CCY1,\n"
            + " T.MID_RATE,\n"
            + " T.BUY_RATE,\n"
            + " T.SALE_RATE\n"
            + " FROM UBS1203.CYTM_RATES t\n"
            + " WHERE (:branchCode is null or branch_code = :branchCode)\n"
            + " and t.rate_date = (SELECT max(x.rate_date) FROM UBS1203.CYTM_RATES x WHERE x.ccy1 = t.ccy1\n"
            + " and x.branch_code = t.branch_code and x.rate_type = t.rate_type)\n"
            + " and T.ccy2='AZN'\n"
            + " and (:rateType is null or t.rate_type=:rateType)\n"
            + " ORDER BY T.BRANCH_CODE ASC, T.RATE_TYPE ASC, T.CCY1";

    public static final String GET_APPLICATION_DATE_BY_CONTRACT_NO = "select application_date "
            + "from iba_app.iba_channel_apps dfg "
            + "where dfg.application_id in "
            + "(select ss.application_id "
            + "from iba_app.iba_channel_apps_attributes ss "
            + "where ss.attribute_value like '%'||:contractNo||'%' "
            + "and ss.attribute_id='LOAN_REF_NUMBER')";
}
