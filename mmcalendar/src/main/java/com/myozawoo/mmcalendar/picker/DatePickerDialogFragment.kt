package com.myozawoo.mmcalendar.picker

import android.app.Dialog
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView

import androidx.coordinatorlayout.widget.CoordinatorLayout

import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.myozawoo.mmcalendar.CalendarDay
import com.myozawoo.mmcalendar.DateListener
import com.myozawoo.mmcalendar.R
import kotlinx.android.synthetic.main.dialog_fragment_date_picker.*
import mmcalendar.MyanmarDateConverter
import java.text.SimpleDateFormat
import java.util.*

class DatePickerDialogFragment(private val listener: DateListener) : BottomSheetDialogFragment(),
    DateListener {

    private val DEFAULT_MONTHS = listOf("JANUARY ပြာသို-တပို့တွဲ", "FEBRUARY တပို့တွဲ-တပေါင်း", "MARCH တပေါင်း-နှောင်းတန်ခူး", "APRIL နှောင်းတန်ခူး-တန်ခူး-ကဆုန်","MAY ကဆုန်-နယုန်",
        "JUNE နယုန်-ပဝါဆို", "JULY ပဝါဆို-ဒုဝါဆို", "AUGUST ဒုဝါဆို-ဝါခေါင်", "SEPTEMBER ဝါခေါင်-တော်သလင်း", "OCTOBER တော်သလင်း-သီတင်းကျွတ်","NOVEMBER သီတင်းကျွတ်-တန်ဆောင်မုန်း",
        "DECEMBER တန်ဆောင်မုန်း-နတ်တော်")

    private val yearList = listOf(
        "2020 ၁၃၈၁-၁၃၈၂",
        "2019 ၁၃၈၀-၁၃၈၁",
        "2018 ၁၃၇၉-၁၃၈၀",
        "2017 ၁၃၇၈-၁၃၇၉",
        "2016 ၁၃၇၇-၁၃၇၈",
        "2015 ၁၃၇၆-၁၃၇၇",
        "2014 ၁၃၇၅-၁၃၇၆",
        "2013 ၁၃၇၄-၁၃၇၅",
        "2012 ၁၃၇၃-၁၃၇၄",
        "2011 ၁၃၇၂-၁၃၇၃",
        "2010 ၁၃၇၁-၁၃၇၂",
        "2009 ၁၃၇၀-၁၃၇၁",
        "2008 ၁၃၆၉-၁၃၇၀",
        "2007 ၁၃၆၈-၁၃၆၉",
        "2006 ၁၃၆၇-၁၃၆၈",
        "2005 ၁၃၆၆-၁၃၆၇",
        "2004 ၁၃၆၅-၁၃၆၆",
        "2003 ၁၃၆၄-၁၃၆၅",
        "2002 ၁၃၆၃-၁၃၆၄",
        "2001 ၁၃၆၂-၁၃၆၃",
        "2000 ၁၃၆၁-၁၃၆၂",
        "1999 ၁၃၆၀-၁၃၆၁",
        "1998 ၁၃၅၉-၁၃၆၀",
        "1997 ၁၃၅၈-၁၃၅၉",
        "1996 ၁၃၅၇-၁၃၅၈",
        "1995 ၁၃၅၆-၁၃၅၇",
        "1994 ၁၃၅၅-၁၃၅၆",
        "1993 ၁၃၅၄-၁၃၅၅",
        "1992 ၁၃၅၃-၁၃၅၄",
        "1991 ၁၃၅၂-၁၃၅၃",
        "1990 ၁၃၅၁-၁၃၅၂",
        "1989 ၁၃၅၀-၁၃၅၁",
        "1988 ၁၃၄၉-၁၃၅၀",
        "1987 ၁၃၄၈-၁၃၄၉",
        "1986 ၁၃၄၇-၁၃၄၈",
        "1985 ၁၃၄၆-၁၃၄၇",
        "1984 ၁၃၄၅-၁၃၄၆",
        "1983 ၁၃၄၄-၁၃၄၅",
        "1982 ၁၃၄၃-၁၃၄၄",
        "1981 ၁၃၄၂-၁၃၄၃",
        "1980 ၁၃၄၁-၁၃၄၂",
        "1979 ၁၃၄၀-၁၃၄၁",
        "1978 ၁၃၃၉-၁၃၄၀",
        "1977 ၁၃၃၈-၁၃၃၉",
        "1976 ၁၃၃၇-၁၃၃၈",
        "1975 ၁၃၃၆-၁၃၃၇",
        "1974 ၁၃၃၅-၁၃၃၆",
        "1973 ၁၃၃၄-၁၃၃၅",
        "1972 ၁၃၃၃-၁၃၃၄",
        "1971 ၁၃၃၂-၁၃၃၃",
        "1970 ၁၃၃၁-၁၃၃၂",
        "1969 ၁၃၃၀-၁၃၃၁",
        "1968 ၁၃၂၉-၁၃၃၀",
        "1967 ၁၃၂၈-၁၃၂၉",
        "1966 ၁၃၂၇-၁၃၂၈",
        "1965 ၁၃၂၆-၁၃၂၇",
        "1964 ၁၃၂၅-၁၃၂၆",
        "1963 ၁၃၂၄-၁၃၂၅",
        "1962 ၁၃၂၃-၁၃၂၄",
        "1961 ၁၃၂၂-၁၃၂၃",
        "1960 ၁၃၂၁-၁၃၂၂",
        "1959 ၁၃၂၀-၁၃၂၁",
        "1958 ၁၃၁၉-၁၃၂၀",
        "1957 ၁၃၁၈-၁၃၁၉",
        "1956 ၁၃၁၇-၁၃၁၈",
        "1955 ၁၃၁၆-၁၃၁၇",
        "1954 ၁၃၁၅-၁၃၁၆",
        "1953 ၁၃၁၄-၁၃၁၅",
        "1952 ၁၃၁၃-၁၃၁၄",
        "1951 ၁၃၁၂-၁၃၁၃",
        "1950 ၁၃၁၁-၁၃၁၂",
        "1949 ၁၃၁၀-၁၃၁၁",
        "1948 ၁၃၀၉-၁၃၁၀",
        "1947 ၁၃၀၈-၁၃၀၉",
        "1946 ၁၃၀၇-၁၃၀၈",
        "1945 ၁၃၀၆-၁၃၀၇",
        "1944 ၁၃၀၅-၁၃၀၆",
        "1943 ၁၃၀၄-၁၃၀၅",
        "1942 ၁၃၀၃-၁၃၀၄",
        "1941 ၁၃၀၂-၁၃၀၃",
        "1940 ၁၃၀၁-၁၃၀၂",
        "1939 ၁၃၀၀-၁၃၀၁",
        "1938 ၁၂၉၉-၁၃၀၀",
        "1937 ၁၂၉၈-၁၂၉၉",
        "1936 ၁၂၉၇-၁၂၉၈",
        "1935 ၁၂၉၆-၁၂၉၇",
        "1934 ၁၂၉၅-၁၂၉၆",
        "1933 ၁၂၉၄-၁၂၉၅",
        "1932 ၁၂၉၃-၁၂၉၄",
        "1931 ၁၂၉၂-၁၂၉၃",
        "1930 ၁၂၉၁-၁၂၉၂",
        "1929 ၁၂၉၀-၁၂၉၁",
        "1928 ၁၂၈၉-၁၂၉၀",
        "1927 ၁၂၈၈-၁၂၈၉",
        "1926 ၁၂၈၇-၁၂၈၈",
        "1925 ၁၂၈၆-၁၂၈၇",
        "1924 ၁၂၈၅-၁၂၈၆",
        "1923 ၁၂၈၄-၁၂၈၅",
        "1922 ၁၂၈၃-၁၂၈၄",
        "1921 ၁၂၈၂-၁၂၈၃",
        "1920 ၁၂၈၁-၁၂၈၂"
    )
    private var mSelectedDate: CalendarDay? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_fragment_date_picker, container, false)
        return view
    }

    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)
        val view = View.inflate(context, R.layout.dialog_fragment_date_picker, null)
        dialog.setContentView(view)
        val params = (view.parent as View).layoutParams as CoordinatorLayout.LayoutParams
        val behavior = params.behavior
        if (behavior != null && behavior is BottomSheetBehavior) {
            behavior.setBottomSheetCallback(mBottomSheetBehaviorCallback)
            behavior.peekHeight = 2000
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mSelectedDate = CalendarDay(2020, 1, 1)
        calendarView.setListener(this)
        spYear.adapter = YearAdapter(context!!, yearList)
        spMonth.adapter = SpinnerAdapter(context!!, DEFAULT_MONTHS)
        spYear.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (yearList[position].substring(0, 4).toInt() == 2020) {
                    spMonth.adapter = SpinnerAdapter(context!!, DEFAULT_MONTHS)
                }else {
                    spMonth.adapter = SpinnerAdapter(context!!, getMonthListYear(
                        yearList[position].substring(0, 4).toInt()
                    ))
                }
            }
        }

        spMonth.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                val year = yearList[spYear.selectedItemPosition].substring(0, 4).toInt()
                val month = position + 1
                val calendarDay = CalendarDay(
                    year,
                    month,
                    1
                )
                calendarView.setDate(
                    calendarDay
                )
                val mmDate = MyanmarDateConverter.convert(year, month, 1)
                val fortNightDay =
                    if (mmDate.fortnightDayInt == 15) "" else "${mmDate.fortnightDay} ရက်"
                tvBurmeseDate.text =
                    "${mmDate.year} ခုနှစ်၊ ${mmDate.monthName} ${mmDate.moonPhase} $fortNightDay၊ ${mmDate.weekDay}"
                tvEngDate.text = convertDateFormat(calendarDay.getDate().toString())


            }
        }

        tvCancel.setOnClickListener { dismiss() }

        tvOk.setOnClickListener {
            listener.onDateClick(mSelectedDate!!)
            dismiss()
        }
    }

    override fun onDateClick(calendarDay: CalendarDay) {
        mSelectedDate = calendarDay
        val mmDate = MyanmarDateConverter.convert(
            calendarDay.getYear(),
            calendarDay.getMonth(),
            calendarDay.getDay()
        )
        val fortNightDay = if (mmDate.fortnightDayInt == 15) "" else "${mmDate.fortnightDay} ရက်"
        tvBurmeseDate.text =
            "${mmDate.year} ခုနှစ်၊ ${mmDate.monthName} ${mmDate.moonPhase} $fortNightDay၊ ${mmDate.weekDay}"
        tvEngDate.text = convertDateFormat(calendarDay.getDate().toString())

    }

    private fun getYearList(): List<String> {
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val tmpEngYearList = arrayListOf<Int>()
        for (i in 0..100) {
            tmpEngYearList.add(currentYear - i)
        }
        val tmpMixYearList = arrayListOf<String>()
        tmpEngYearList.forEach {
            val tmpMyanmarYearList = arrayListOf<String>()
            for (i in 1..6) {
                val mmDate = MyanmarDateConverter.convert(it, i, 1)
                tmpMyanmarYearList.add(mmDate.year)
            }
            tmpMixYearList.add(
                "${it} (${tmpMyanmarYearList.distinct().joinToString(separator = "-")})"
            )

        }
        return tmpMixYearList
    }

    private fun getMonthListYear(year: Int): List<String> {
        val months = arrayListOf<String>()

        val monthList = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)

        monthList.forEach { month ->

            val engDate = CalendarDay(year, month, 1)
            val maxDate = if (year % 4 != 0 && engDate.getMonth() == 2) {
                28
            } else {
                engDate.getDate().month.maxLength()
            }
            val endDate = CalendarDay.from(engDate.getYear(), engDate.getDate().monthValue, maxDate)
            val tmpBurmeseMonth = arrayListOf<String>()
            for (i in 1 until endDate.getDay()) {
                val mmDate = MyanmarDateConverter.convert(year, month, i)
                if (mmDate.monthName.isNotEmpty()) {
                    tmpBurmeseMonth.add(mmDate.monthName)
                }
            }

            months.add(
                "${engDate.getDate().month.name} ${tmpBurmeseMonth.distinct()
                    .joinToString(separator = "-")}"
            )



        }

        return months
    }

    fun convertDateFormat(date: String): String {
        if (date == "") return ""
        val dateFormat = "yyyy-MM-dd"
        val sdf = SimpleDateFormat(dateFormat, Locale.getDefault())
        val date = sdf.parse(date)
        return SimpleDateFormat("yyyy, MMMM dd EEEE", Locale.getDefault()).format(date)
    }

    private val mBottomSheetBehaviorCallback = object : BottomSheetBehavior.BottomSheetCallback() {
        override fun onSlide(bottomSheet: View, slideOffset: Float) {

        }

        override fun onStateChanged(bottomSheet: View, newState: Int) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
//                dismiss()
            }
        }
    }
}